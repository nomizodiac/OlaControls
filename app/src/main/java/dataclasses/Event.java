package dataclasses;

public class Event {

    private String time;
    private String msg;

    public Event(String time,String msg)
    {
        this.msg=msg;
        this.time=time;
    }

    public String getMsg() {
        return msg;
    }

    public String getTime() {
        return time;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
