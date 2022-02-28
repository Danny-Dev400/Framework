package TestForTMDB;

public class Session {

    private static Session log;
    private final String SessionId;

    private Session(String _SessionId){
        this.SessionId = _SessionId;
    }

    public static Session getInstance(String _SessionId){
        if (log == null){
            log = new Session(_SessionId);
        }else {
            System.out.println("La secion ya esta iniciada");
        }
        return log;
    }

    public String getSessionId(){
        return this.SessionId;
    }

}
