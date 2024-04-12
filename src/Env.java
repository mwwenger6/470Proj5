import java.util.HashMap;
import java.util.Map;

public class Env {
    private Map<String, Object> table;
    public Env prev;

    public Env(Env p) {
        this.prev = p;
        this.table = new HashMap<>();
    }

    public void Put(String name, Object value) {
        table.put(name, value);
    }

    public Object Get(String name) {
        for (Env e = this; e != null; e = e.prev) {
            Object found = e.table.get(name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
