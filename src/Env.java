import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

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
    public Object GetLocal(String name) {
        return table.get(name);
    }
    
    public void printEnvironment() {
        table.entrySet().forEach(entry -> {
            System.out.println("Scope " + entry.getKey() + " -> " + entry.getValue());
        });
    }
    
}
