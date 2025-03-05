package korweb.service;

public class TaskService {
    // (3) 싱글톤
    private static final TaskService instance = new TaskService();
    private TaskService(){}
    public static TaskService getInstance(){return instance;}

    public void method1(){}
    // (4) 메소드자체가 static
    public static void method2(){}
}
