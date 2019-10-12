package work.codehub.library.helper;

import work.codehub.library.pojo.AuthorVO;

/**
 * 线程变量仓库
 */
public class LocalStore {

    private static ThreadLocal<AuthorVO> author = new ThreadLocal<>();

    private static ThreadLocal<String> token = new ThreadLocal<>();

    public static AuthorVO getAuthor() {
        return author.get();
    }

    public static void setAuthor(AuthorVO author) {
        LocalStore.author.set(author);
    }

    public static String getToken() {
        return token.get();
    }

    public static void setToken(String token) {
        LocalStore.token.set(token);
    }

    public static void destory() {
        LocalStore.token.remove();
    }
}
