package domain;

public class DomainInterface {

    private static System system = new System();

    public static void saveProduction(Production production){
        try {
            system.saveProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    public static void removeProduction(Production production){
        try {
            system.removeProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    public static Production getProduction(long ID){
        return system.getProduction(ID);
    }

    public static void editProduction(Production production){
        try {
            system.editProduction(production);
        } catch (Exception e) {
            e.printStackTrace();
            java.lang.System.out.println(e.getMessage());
        }
    }

    public static System getSystem() {
        return system;
    }
}
