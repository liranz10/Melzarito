package sapir_liran.melzarito.UI;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MelzaritoFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private String token;
    @Override
    public void onTokenRefresh() {
        token= FirebaseInstanceId.getInstance().getToken();
    }
}
