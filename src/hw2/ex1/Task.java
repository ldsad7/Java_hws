package hw2.ex1;


/**
 * Ex1:
 * Вы разрабатываете небольшое приложение по аннотированию (разметке) изображений
 * с целью последующего использования этой разметки для обучения моделей computer vision.
 * В этом приложении пользователь может выделять области на изображении
 * с помощью прямоугольников и окружностей и подписывать их произвольным текстом.
 * Прямоугольники определяются координатами левого нижнего и правого верхнего углов,
 * а окружности - координатами центра и радиусом.
 * Вся разметка для изображения представляется массивом Annotation[].
 * Определите класс Annotation для представления данных разметки (подпись + фигура)
 * и классы Figure, BoundingBox, Circle для задания размеченных областей.
 * <p>
 * Ex2:
 * От пользователей приложения пришел запрос на возможности перемещать уже размеченные области.
 * Для поддержки новой функциональности вам требуется внести несколько изменений:
 * 1) выбор аннотации по координатам точки (x, y);
 * В массиве аннотаций требуется найти первую, фигура которой содержит точку с заданными координатами.
 * Annotation findByPoint(Annotation[] annotations, int x, int y) {
 * // ...
 * }
 * 2) перемещение фигуры выбранной аннотации на смещение (dx, dy);
 * В рамках этого изменения вы решили доработать классы фигур таким образом, чтобы они реализовывали интерфейс
 * public interface Movable {
 * void move(int dx, int dy);
 * }
 * Доработайте классы и реализуйте метод findByPoint.
 */
public class Task {
    public static void main(String[] args) {
        Annotation[] annotations = new Annotation[]{
                new Annotation(new Circle(new Dot(4, 5), 5), new Caption("a dog")),
                new Annotation(new BoundingBox(new Dot(10, 2), new Dot(14, 4)), new Caption("a fox")),
                new Annotation(new Circle(new Dot(10, 2), 2.7), new Caption("a cat")),
                new Annotation(new BoundingBox(new Dot(5, 5), new Dot(7, 9)), new Caption("a wolf")),
                // The following lines will cause an exit with an error:
//                new Annotation(new BoundingBox(new Dot(5, 5), new Dot(7, 9)), new Caption("")),
//                new Annotation(new BoundingBox(new Dot(5, 5), new Dot(7, 9)), new Caption(null)),
//                new Annotation(new BoundingBox(new Dot(5, 5), new Dot(7, 9)), new Caption("   ")),
//                new Annotation(new Circle(new Dot(5, 10), -1), new Caption("  a bear ")),
//                new Annotation(new Circle(new Dot(5, 10), 0), new Caption("  a bear ")),
//                new Annotation(new Circle(new Dot(-0.01, 1), 1), new Caption("  a bear ")),
//                new Annotation(new Circle(new Dot(1, -0.01), 1), new Caption("  a bear ")),
//                new Annotation(new BoundingBox(new Dot(7.1, 10), new Dot(7, 9)), new Caption("  a bear ")),
//                new Annotation(new BoundingBox(new Dot(6, 10), new Dot(7, 9)), new Caption("  a bear ")),
//                new Annotation(new BoundingBox(new Dot(8, 8), new Dot(7, 9)), new Caption("  a bear ")),
//                new Annotation(new BoundingBox(new Dot(-1, 1), new Dot(7, 9)), new Caption("  a bear "))
        };
        System.out.println("toString:");
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
            System.out.println(String.format("\tArea of the Figure: %.2f\n", annotation.figure.countArea()));
        }

        double[] xs = new double[]{8, 10, 11, 10, 0, 0}; // , -1
        double[] ys = new double[]{5, 5, 3, 3, 5, 5}; // , 10
        System.out.println("findByPoint:");
        for (int i = 0; i < xs.length; ++i) {
            System.out.println(String.format("%s", findByPoint(annotations, new Dot(xs[i], ys[i]))));
        }

        System.out.println("\nmove:");
        Vector vector = new Vector(2, 5);
//        Vector vector = new Vector(-15, -15);
        for (Annotation annotation : annotations) {
            System.out.println(String.format("Before: %s", annotation));
            annotation.figure.move(vector);
            System.out.println(String.format("After: %s\n", annotation));
        }
    }

    public static void exitWithError(String errorMessage) {
        System.err.println(errorMessage);
        System.exit(1);
    }

    public static Annotation findByPoint(Annotation[] annotations, Dot dot) {
        for (Annotation annotation : annotations) {
            if (annotation.figure.checkIfInside(dot)) {
                return annotation;
            }
        }
//        exitWithError(String.format("There is no annotation that contains this dot: %s", dot));
        return null;
    }

    public interface Movable {
        void move(Vector vector);
    }

    public interface MinkowskiDistance {
        default double countMinkowskiDistance(double dx, double dy, double p) {
            if (p < 1) {
                exitWithError(String.format("p (%f) should be >= 1", p));
            }
            return Math.pow(Math.pow(Math.abs(dx), p) + Math.pow(Math.abs(dy), p), 1. / p);
        }
    }

    public abstract static class Figure implements Movable {
        public abstract double countArea();

        public abstract boolean checkIfInside(Dot dot);
    }

    public static class Annotation {
        public Figure figure;
        public Caption caption;

        Annotation(Figure figure, Caption caption) {
            this.figure = figure;
            this.caption = caption;
        }

        @Override
        public String toString() {
            return String.format("Annotation:\n\tFigure: %s\n\tand\n\tCaption: \"%s\"", this.figure, this.caption);
        }
    }

    public static class Caption {
        private String caption;

        Caption(String caption) {
            if (caption == null || caption.trim().isEmpty()) {
                exitWithError(
                        String.format("Caption (\"%s\") can't be null or empty (containing only space symbols)", caption)
                );
            }
            this.caption = caption;
        }

        @Override
        public String toString() {
            return String.format("Caption with string \"%s\"", this.caption);
        }
    }

    public static class Vector implements MinkowskiDistance {
        private double dx;
        private double dy;

        Vector(double dx, double dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public double getLength() {
            return this.countMinkowskiDistance(dx, dy, 2);
        }
    }

    public static class Dot implements MinkowskiDistance {
        private double x;
        private double y;

        Dot(double x, double y) {
            if (x < 0 || y < 0) {
                exitWithError(String.format("`x` (%.2f) and `y` (%.2f) should not be negative", x, y));
            }
            this.x = x;
            this.y = y;
        }

        private double getXDiff(Dot dot) {
            return dot.x - this.x;
        }

        private double getYDiff(Dot dot) {
            return dot.y - this.y;
        }

        /**
         * Counts distance between two dots (Minkowski's p = 2)
         *
         * @param dot we count the distance from
         * @return distance
         */
        private double countDistance(Dot dot) {
            return this.countMinkowskiDistance(this.getXDiff(dot), this.getYDiff(dot), 2);
        }

        public void iAdd(Vector vector) {
            if (this.x + vector.dx < 0 || this.y + vector.dy < 0) {
                exitWithError(
                        String.format(
                                "Adding vector to the dot (%s) results in a dot with negative coordinates (`x`: %.2f, `y`: %.2f)",
                                this, this.x + vector.dx, this.y + vector.dy
                        )
                );
            }
            this.x += vector.dx;
            this.y += vector.dy;
        }

        public Dot add(Vector vector) {
            // if (this.x + vector.dx < 0 || this.y + vector.dy < 0) then it will fail in the constructor.
            return new Dot(this.x + vector.dx, this.y + vector.dy);
        }

        public Vector getDiffVector(Dot dot) {
            return new Vector(dot.x - this.x, dot.y - this.y);
        }

        public Vector getXDiffVector(Dot dot) {
            return new Vector(this.getXDiff(dot), 0);
        }

        public Vector getYDiffVector(Dot dot) {
            return new Vector(0, this.getYDiff(dot));
        }

        public boolean isToTheLeft(Dot dot) {
            return this.x < dot.x;
        }

        public boolean isLower(Dot dot) {
            return this.y < dot.y;
        }

        @Override
        public String toString() {
            return String.format("Dot with coordinates (%.2f; %.2f)", x, y);
        }
    }

    public static class BoundingBox extends Figure {
        private Dot leftBottom;
        private Dot rightTop;

        BoundingBox(Dot leftBottom, Dot rightTop) {
            if (!leftBottom.isToTheLeft(rightTop) || !leftBottom.isLower(rightTop)) {
                exitWithError(String.format(
                        "`leftBottom` dot (%s) should be located to the left and lower than `rightTop` dot (%s)",
                        leftBottom, rightTop)
                );
            }
            this.leftBottom = leftBottom;
            this.rightTop = rightTop;
        }

        @Override
        public double countArea() {
            return this.leftBottom.getXDiffVector(this.rightTop).getLength() *
                    this.leftBottom.getYDiffVector(this.rightTop).getLength();
        }

        @Override
        public boolean checkIfInside(Dot dot) {
            return this.leftBottom.isToTheLeft(dot) && this.leftBottom.isLower(dot) &&
                    !this.rightTop.isToTheLeft(dot) && !this.rightTop.isLower(dot);
        }

        @Override
        public void move(Vector vector) {
            this.leftBottom.iAdd(vector);
            this.rightTop.iAdd(vector);
        }

        @Override
        public String toString() {
            return String.format(
                    "BoundingBox with dots leftBottom (%s) and rightTop (%s)", this.leftBottom, this.rightTop
            );
        }
    }

    public static class Circle extends Figure {
        private Dot center;
        private double radius;

        Circle(Dot center, double radius) {
            if (radius <= 0) {
                exitWithError(String.format("`radius` (%.2f) should be positive", radius));
            }
            this.center = center;
            this.radius = radius;
        }

        @Override
        public double countArea() {
            return Math.PI * Math.pow(this.radius, 2);
        }

        @Override
        public boolean checkIfInside(Dot dot) {
            return this.center.countDistance(dot) <= this.radius;
        }

        @Override
        public void move(Vector vector) {
            this.center.iAdd(vector);
        }

        @Override
        public String toString() {
            return String.format("Circle with dot center (%s) and radius (%.2f)", this.center, this.radius);
        }
    }
}