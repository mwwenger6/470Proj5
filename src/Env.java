import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Env {
    private Map<String, Object> table;
    private HashMap<String, String> functionTypes = new HashMap<>();
    private HashMap<String, Boolean> functionReturns = new HashMap<>();
    private HashMap<String, ArrayList<String>> paramTypes = new HashMap<>(); 
    private HashMap<String, ArrayList<String>> paramIdents = new HashMap<>();
    private Map<String, Integer> addressMap; 
    private int localAddressCounter = 0; 
    private String currentFunction = null; 
    public Env prev;

    public Env(Env p) {
        this.prev = p;
        this.table = new HashMap<>();
        this.addressMap = new HashMap<>();
        this.localAddressCounter = 1;
    }

    public void Put(String name, Object value) {
        table.put(name, value);
        addressMap.put(name, localAddressCounter++);
    }
    public void putFunctionType(String funcName, String type) {
        functionTypes.put(funcName + "()", type);
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
    public String getFunctionType(String funcName) {
        for (Env e = this; e != null; e = e.prev) {
            String type = e.functionTypes.get(funcName);
            if (type != null) {
                return type;
            }
        }
        return null;
    }
    public void setCurrentFunction(String funcName) {
        currentFunction = funcName + "()";
        functionReturns.put(currentFunction, false); // Initialize with no return encountered
    }

    public void setParamTypes(String funcName, ArrayList<String> types) {
        paramTypes.put(funcName, new ArrayList<>(types));  // Copy to ensure isolation from external changes
    }
    public ArrayList<String> getParamTypes(String funcName) {
        for (Env e = this; e != null; e = e.prev) {
            ArrayList<String> types = e.paramTypes.get(funcName);
            if (types != null) {
                return new ArrayList<>(types);
            }
        }
        return new ArrayList<>();
    }

    public void setParamIdents(String funcName, ArrayList<String> idents) {
        paramIdents.put(funcName, new ArrayList<>(idents));
    }

    public ArrayList<String> getParamIdents(String funcName) {
        for (Env e = this; e != null; e = e.prev) {
            ArrayList<String> idents = e.paramIdents.get(funcName);
            if (idents != null) {
                return new ArrayList<>(idents);
            }
        }
        return new ArrayList<>();
    }
    public Integer getAddress(String name) {
        for (Env e = this; e != null; e = e.prev) {
            if (e.addressMap.containsKey(name)) {
                return e.addressMap.get(name);
            }
        }
        return null; 
    }

    public String getCurrentFunction() {
        return currentFunction;
    }
    public void clearCurrentFunction() {
        currentFunction = null;
    }
    public void markReturnEncountered() {
        if (currentFunction != null) {
            functionReturns.put(currentFunction, true);
        }
    }
    public boolean isReturnEncountered() {
        return functionReturns.getOrDefault(currentFunction, false);
    }

}