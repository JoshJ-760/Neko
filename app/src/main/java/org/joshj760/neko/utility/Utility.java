package org.joshj760.neko.utility;

public class Utility {

    private Utility() {
        //intentionally blank
    }

    public static <T> T getRandomItem(T[] items) {
        return items[randomInt(items.length)];
    }

    public static int randomInt(int maxExclusive) {
        return (int)(Math.random() * ((double)maxExclusive));
    }

    public static int randomXInBounds(BoundingBox boundingBox) {
        int relativePos = randomInt(Math.abs(boundingBox.getX2() - boundingBox.getX1()));
        return relativePos + Math.min(boundingBox.getX1(), boundingBox.getX2());
    }

    public static int randomYInBounds(BoundingBox boundingBox) {
        int relativePos = randomInt(Math.abs(boundingBox.getY2() - boundingBox.getY1()));
        return relativePos + Math.min(boundingBox.getY1(), boundingBox.getY2());
    }

    /** Normalize an angle so that it is between 0 and 360.
     *
     * @param angle Angle in degrees to normalize
     * @return Normalized angle.
     **/
    public static double normalize(final double angle) {
        return (angle >= 0 ? angle : (360 - ((-angle) % 360))) % 360;
    }
}
