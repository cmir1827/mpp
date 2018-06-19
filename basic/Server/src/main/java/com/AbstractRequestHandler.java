package com;

import com.google.gson.Gson;
import com.model.*;
import com.net.NetRequest;
import com.net.NetResponse;
import com.net.ResponseType;
import com.repositories.MasinaHBNRepositpry;
import com.repositories.MasinaPunctControlRepository;
import com.repositories.PunctControlRepository;
import com.repositories.UserRepo;
import com.services.MasinaPunctControlService;
import com.services.MasinaService;
import com.services.PunctControlService;
import com.services.UsersService;
import com.util.JdbcUtils;
import jdk.nashorn.internal.scripts.JD;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public abstract class AbstractRequestHandler implements Runnable{

    Gson gson = new Gson();

    UsersService usersService = new UsersService(new UserRepo(JdbcUtils.getProps()));
    MasinaService masinaService = new MasinaService(new MasinaHBNRepositpry());
    PunctControlService punctControlService = new PunctControlService(new PunctControlRepository(JdbcUtils.getProps()));
    MasinaPunctControlService masinaPunctControlService = new MasinaPunctControlService(new MasinaPunctControlRepository(JdbcUtils.getProps()));

    public AbstractRequestHandler() { }

    @Override
    public abstract void run() ;

    protected Map<TSUser, String> userMap = new HashMap<>();

    public Map<TSUser, String> getUserMap() {
        return userMap;
    }

    protected NetResponse handleSignIn(String usrJsonString, String senderId){
        TSUser user = gson.fromJson(usrJsonString, TSUser.class);
        TSUser found = usersService.findByUsername(user.getUsername());

        if(found != null){
            if(found.getPassword().equals(user.getPassword())){
                if(this.userMap.containsKey(found)){
                    return new NetResponse(ResponseType.ERROR,"User Already logged in");
                }else{
                    this.userMap.put(found,senderId);
                }
                return new NetResponse(ResponseType.OK, "SignIn cu success", gson.toJson(found.getUserId()));
            }else{
                return new NetResponse(ResponseType.ERROR,"Imi pare rau, parola gresita!" );
            }
        }else{
            return new NetResponse(ResponseType.ERROR, "Imi pare rau, username invalid!" );
        }
    }

    protected NetResponse handleLogOut(String usrJsonString, String senderId){
        TSUser user = gson.fromJson(usrJsonString, TSUser.class);

        Optional<TSUser> foundUser = userMap.keySet().stream().filter(p -> p.getUsername().equals(user.getUsername())).findFirst();
        if (foundUser.isPresent()){
            userMap.remove(foundUser.get());
            return new NetResponse(ResponseType.OK, "Log out cu success");
        }else{
            return new NetResponse(ResponseType.ERROR, "User is not logged in!");
        }
    }

    protected NetResponse handleGetMasini(String usrJsonString) {
        TSUser user = gson.fromJson(usrJsonString, TSUser.class);
        System.out.println(user);

        PunctControl punctControl = punctControlService.getAll().stream().filter((p) -> p.getUser().getUserId() == user.getUserId()).findFirst().get();

        if (punctControl.getNumarControl() == 0) {
            //send all cars

            Comparator<MasinaPunctControl> byPunct = new Comparator<MasinaPunctControl>() {
                @Override
                public int compare(MasinaPunctControl o1, MasinaPunctControl o2) {
                    return o1.getPunctControl().getNumarControl().compareTo(o2.getPunctControl().getNumarControl());
                }
            };
            List<MasinaPunctControl> masinas = masinaPunctControlService.findAll();
            List<Integer> masinaIds = masinas.stream().map(m -> m.getMasina().getId()).collect(Collectors.toList());

            List<MasinaPunctControl> pct = new ArrayList<>();
            for (Integer mId : masinaIds) {
                MasinaPunctControl pc = masinas.stream().filter(m -> m.getMasina().getId() == mId).max(byPunct).get();
                pct.add(pc);
            }

            MasinaPunctControl[] masinaArray = new MasinaPunctControl[pct.size()];
            masinaArray = pct.toArray(masinaArray);

            return new NetResponse(ResponseType.OK, "message", gson.toJson(masinaArray));
        } else {
            List<MasinaPunctControl> allMasinas = masinaPunctControlService.findAll();
            List<MasinaPunctControl> masinas = masinaPunctControlService.findAll().stream().filter((p) -> p.getPunctControl().getNumarControl() == (punctControl.getNumarControl() - 1)).collect(Collectors.toList());

            List<MasinaPunctControl> finalList = new ArrayList<>();

            for (MasinaPunctControl masinaPunctControl : masinas) {
                Comparator<MasinaPunctControl> byPunct = new Comparator<MasinaPunctControl>() {
                    @Override
                    public int compare(MasinaPunctControl o1, MasinaPunctControl o2) {
                        return o1.getPunctControl().getNumarControl().compareTo(o2.getPunctControl().getNumarControl());
                    }
                };

                if (allMasinas.stream().filter((pc) -> pc.getMasina().getId() == masinaPunctControl.getMasina().getId()).max(byPunct).get().getId() == masinaPunctControl.getId()) {
                    finalList.add(masinaPunctControl);
                }
            }
            MasinaPunctControl[] masinaArray = new MasinaPunctControl[finalList.size()];
            masinaArray = finalList.toArray(masinaArray);

            return new NetResponse(ResponseType.OK, "message", gson.toJson(masinaArray));
        }
    }

    protected NetResponse handlePassCheckpoint(String punctControlJson) {
        MasinaPunctControl control = gson.fromJson(punctControlJson, MasinaPunctControl.class);

        Optional<PunctControl> optional = punctControlService.getAll().stream().filter(p -> p.getNumarControl() == (control.getPunctControl().getNumarControl() + 1)).findFirst();
        if (optional.isPresent()) {
            control.setPunctControl(optional.get());
            masinaPunctControlService.save(control);

            List<TSUser> punctControls = punctControlService.getAll().stream().filter((p) -> p.getNumarControl() == control.getPunctControl().getNumarControl() + 1 || p.getNumarControl() == 0).map((t) -> t.getUser()).collect(Collectors.toList());

            List<String> sentTo = new ArrayList<>();
            for(TSUser crt : punctControls) {
                Optional<TSUser> foundUser = userMap.keySet().stream().filter(p -> p.getUsername().equals(crt.getUsername())).findFirst();
                if (foundUser.isPresent()) {
                    if (userMap.containsKey(foundUser.get()) &&  !sentTo.stream().anyMatch(p -> p.equals(foundUser.get().getUsername()))) {
                        sendCustomNotification(userMap.get(foundUser.get()), new NetResponse(ResponseType.Notify_new_car, "OK", gson.toJson(control, MasinaPunctControl.class)));
                        sentTo.add(foundUser.get().getUsername());
                    }
                }
            }

            return new NetResponse(ResponseType.OK, "ok");
        } else {
            return new NetResponse(ResponseType.ERROR, "last checkpiont");
        }


    }

    public NetResponse getResponseForRequest(NetRequest request, String senderId){
        if(request != null){
            NetResponse response;
            switch (request.getRequestType()) {
                case SignIn:
                    response = handleSignIn(request.getJsonString(), senderId);
                    return response;
                case LogOut:
                    response = handleLogOut(request.getJsonString(), senderId);
                    return response;
                case GetMasini:
                    response = handleGetMasini(request.getJsonString());
                    return response;
                case PassCheckpoint:
                    response = handlePassCheckpoint(request.getJsonString());
                    return response;
            }
        }
        return null;
    }


    public abstract void sendCustomNotification(String destination, NetResponse response);

    public abstract void sendNotification(NetResponse response);
}
