package lt.techin.Schedule.shift;

public enum LessonTime {

    FIRST("1", new float[]{8.00f, 8.55f}), SECOND("2", new float[]{8.55f, 9.40f}),
    THIRD("3", new float[]{9.50f, 10.35f}), FOURTH("4", new float[]{10.45f, 11.30f}),
    FIFTH("5", new float[]{12.00f, 12.45f}), SIXTH("6", new float[]{12.55f, 13.40f}),
    SEVENTH("7", new float[]{13.50f, 14.35f}), EIGHTH("8", new float[]{14.45f, 15.30f}),
    NINTH("9", new float[]{15.40f, 16.25f}), TENTH("10", new float[]{16.35f, 17.20f}),
    ELEVENTH("11", new float[]{17.30f, 18.15f}), TWELFTH("12", new float[]{18.25f, 19.10f});

    private final int lessonNumber;
    private final float lessonStart;
    private final float lessonEnd;

    LessonTime(String s, float[] floatArr) {
        lessonNumber = Integer.parseInt(s);
        lessonStart = floatArr[0];
        lessonEnd = floatArr[1];
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public float getLessonStart() {
        return lessonStart;
    }

    public float getLessonEnd() {
        return lessonEnd;
    }
}
