package hello.core.singleton;

import javax.xml.bind.SchemaOutputResolver;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    private SingletonService() {
        // 생성자를 private으로 막아서 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막음
    }

    public static SingletonService getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
