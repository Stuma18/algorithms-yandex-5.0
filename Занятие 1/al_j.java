import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static int x_cur = 0;
    public static int y_cur = 0;
    public static int y_new = 0;
    public static List < int[] > rectangles = new ArrayList < > ();
    public static String answer = "";
    public static int is_space = 0;
    public static int x_abs = 0;
    public static int y_abs = 0;
    public static int y_abs_next = 0;
    public static boolean is_float = false;

    public static class Image {
        public String layout;
        public int width;
        public int height;
        public int dx = 0;
        public int dy = 0;
    }

    public static Image proceed_img(String imgParams) {
        Image image = new Image();
        for (String param: imgParams.split(" ")) {
            if (param.startsWith("layout")) {
                image.layout = param.substring(7);
            }
            if (param.startsWith("width")) {
                image.width = Integer.parseInt(param.substring(6));
            }
            if (param.startsWith("height")) {
                image.height = Integer.parseInt(param.substring(7));
            }
            if (param.startsWith("dx")) {
                image.dx = Integer.parseInt(param.substring(3));
            }
            if (param.startsWith("dy")) {
                image.dy = Integer.parseInt(param.substring(3));
            }
        }
        return image;
    }

    public static List < int[] > compute_rectangles(int totalHeight, int width, List < int[] > rectangles) {
        Collections.sort(rectangles, (a, b) -> Integer.compare(a[0], b[0]));
        List < int[] > fragments = new ArrayList < > ();
        int currentWidth = 0;
        for (int[] rectangle: rectangles) {
            int startWidth = rectangle[0];
            int endWidth = rectangle[1];
            int endHeight = rectangle[3];
            if (endHeight > totalHeight) {
                fragments.add(new int[] {
                    currentWidth,
                    startWidth
                });
                currentWidth = endWidth;
            }
        }
        fragments.add(new int[] {
            currentWidth, width
        });
        return fragments;
    }

    public static void proceed_line(int w, int h, int c, String line) {
        if (line.replaceAll(" ", "").equals("")) {
            x_cur = 0;
            y_abs = y_abs_next;
            y_cur = y_new;
            y_new += h;
            x_abs = x_cur;
            is_space = 0;
        }
        String[] lineSplit = line.split("\\(|\\)");
        List < int[] > fragments = new ArrayList < > ();
        for (int i = 0; i < lineSplit.length; i++) {
            if (lineSplit[i].startsWith("image") && lineSplit[i].contains("layout=")) {
                Image curImage = proceed_img(lineSplit[i]);
                switch(curImage.layout) {
                    case "embedded":
                        is_float = false;
                        boolean fl = false;
                        while(!fl) {
                            fragments = compute_rectangles(y_cur, w, rectangles);
                            if (fragments.size() > 0) {
                                for (int[] fragment: fragments) {
                                    x_cur = Math.max(x_cur, fragment[0]);
                                    x_abs = x_cur;
                                    if (x_cur >= fragment[0] && x_cur <= fragment[1] && x_cur + curImage.width <= fragment[1]) {
                                        if (x_cur + (c - is_space) <= fragment[1] || x_cur == fragment[0]) {
                                            answer += x_cur + " " + y_cur + "\n";
                                            if (x_cur + curImage.width + c <= fragment[1]) {
                                                x_cur = x_cur + curImage.width + c;
                                                x_abs = x_cur;
                                                is_space = c;
                                            } else {
                                                x_cur = x_cur + curImage.width;
                                                x_abs = x_cur;
                                                is_space = 0;
                                            }
                                            if (curImage.height <= h) {
                                                y_new = Math.max(y_cur + h, y_new);
                                                y_abs_next = y_new;
                                            } else {
                                                y_new = Math.max(y_new, y_cur + curImage.height);
                                                y_abs_next = y_new;
                                            }
                                            fl = true;
                                            break;
                                        } else {
                                            x_cur = Math.max(x_cur, fragment[1]);
                                            x_abs = x_cur;
                                        }
                                    } else {
                                        x_cur = Math.max(x_cur, fragment[1]);
                                        x_abs = x_cur;
                                    }
                                }
                                if (!fl) {
                                    y_cur = y_new;
                                    y_new += h;
                                    x_cur = 0;
                                    x_abs = x_cur;
                                    y_abs = y_cur;
                                }
                            }
                        }
                        break;
                    case "surrounded":
                        is_float = false;
                        fl = false;
                        if (is_space != 0) {
                            x_cur -= c;
                            x_abs = x_cur;
                        }
                        while(!fl) {
                            fragments = compute_rectangles(y_cur, w, rectangles);
                            if (fragments.size() > 0) {
                                for (int[] fragment: fragments) {
                                    x_cur = Math.max(x_cur, fragment[0]);
                                    x_abs = x_cur;
                                    if (x_cur >= fragment[0] && x_cur <= fragment[1] && x_cur + curImage.width <= fragment[1]) {
                                        answer += x_cur + " " + y_cur + "\n";
                                        rectangles.add(new int[] {
                                            x_cur,
                                            x_cur + curImage.width,
                                                y_cur,
                                                y_cur + curImage.height
                                        });
                                        x_cur += curImage.width;
                                        y_new = Math.max(y_cur + h, y_new);
                                        x_abs = x_cur;
                                        fl = true;
                                        y_abs_next = y_new;
                                        y_abs = y_cur;
                                        break;
                                    } else {
                                        x_cur = Math.max(x_cur, fragment[1]);
                                        x_abs = x_cur;
                                    }
                                }
                                if (!fl) {
                                    y_cur = y_new;
                                    y_new += h;
                                    x_cur = 0;
                                    x_abs = x_cur;
                                    y_abs = y_cur;
                                    y_abs_next = y_new;
                                }
                            }
                        }
                        is_space = 0;
                        break;
                    case "floating":
                        int tg = is_space;
                        if (is_float) {
                            is_space = 0;
                        }
                        if (x_abs + curImage.dx - is_space + curImage.width <= w && x_abs + curImage.dx - is_space >=
                            0) {
                            answer += (x_abs + curImage.dx - is_space) + " " + (y_abs + curImage.dy) + "\n";
                            x_abs = x_abs + curImage.dx - is_space + curImage.width;
                            y_abs = y_abs + curImage.dy;
                            y_abs_next = y_abs + h;
                        } else {
                            if (x_abs + curImage.dx - is_space + curImage.width >= w) {
                                answer += (w - curImage.width) + " " + (y_abs + curImage.dy) + "\n";
                                x_abs = w;
                                y_abs = y_abs + curImage.dy;
                                y_abs_next = y_abs + h;
                            } else {
                                answer += 0 + " " + (y_abs + curImage.dy) + "\n";
                                x_abs = curImage.width;
                                y_abs = y_abs + curImage.dy;
                                y_abs_next = y_abs + h;
                            }
                        }
                        is_float = true;
                        is_space = tg;
                        break;
                }
            } else {
                String[] words = lineSplit[i].split(" ");
                for (String word: words) {
                    if (!word.equals("")) {
                        is_float = false;
                        boolean fl = false;
                        while(!fl) {
                            fragments = compute_rectangles(y_cur, w, rectangles);
                            if (fragments.size() > 0) {
                                for (int[] fragment: fragments) {
                                    x_cur = Math.max(x_cur, fragment[0]);
                                    x_abs = x_cur;
                                    y_abs = y_cur;
                                    if (x_cur >= fragment[0] && x_cur <= fragment[1] && x_cur + word.length() * c <= fragment[1]) {
                                        if (x_cur + (c - is_space) <= fragment[1] || x_cur == fragment[0] || is_space == c) {
                                            if ((x_cur + word.length() * c + c) <= fragment[1]) {
                                                x_cur = x_cur + word.length() * c + c;
                                                is_space = c;
                                                x_abs = x_cur;
                                            } else {
                                                x_cur = x_cur + word.length() * c;
                                                is_space = 0;
                                                x_abs = x_cur;
                                            }
                                            y_new = Math.max(y_cur + h, y_new);
                                            y_abs_next = y_new;
                                            fl = true;
                                            break;
                                        } else {
                                            x_cur = Math.max(x_cur, fragment[1]);
                                            x_abs = x_cur;
                                        }
                                    } else {
                                        x_cur = Math.max(x_cur, fragment[1]);
                                        x_abs = x_cur;
                                    }
                                }
                                if (!fl) {
                                    y_cur = y_new;
                                    y_new += h;
                                    x_cur = 0;
                                    x_abs = x_cur;
                                    y_abs = y_cur;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] data = reader.readLine().split(" ");
            int w = Integer.parseInt(data[0]);
            int h = Integer.parseInt(data[1]);
            int c = Integer.parseInt(data[2]);
            String inputData = "";
            String line = "";

            while(reader.ready()) {
                inputData = reader.readLine();
                if (!inputData.replaceAll(" ", "").equals("")) {
                    line += inputData + " ";
                } else {
                    proceed_line(w, h, c, line);
                    proceed_line(w, h, c, "");
                    line = "";
                }
            }
            proceed_line(w, h, c, line);
            System.out.println(answer);
        } catch(IOException e) {}
    }
}
