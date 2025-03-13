package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

    // TODO entirely your job

    @Override
    public Integer onPolygon(final Polygon p) {
        return 1;
    }

    @Override
    public Integer onCircle(final Circle c) {
        return 1;
    }

    @Override
    public Integer onGroup(final Group g) {
        int count = 0;
        for(Shape shape : g.shapes) {
            if(shape instanceof Location || shape instanceof Group) {
                count += shape.accept(this);
            }else{
                count++;
            }
        }
        return count;
    }

    @Override
    public Integer onRectangle(final Rectangle q) {
        return 1;
    }

    @Override
    public Integer onOutline(final Outline o) {
        return o.shape.accept(this);
    }

    @Override
    public Integer onFill(final Fill c) {
        return c.shape.accept(this);
    }

    @Override
    public Integer onLocation(final Location l) {
        int count = 0;
        count += l.shape.accept(this);
        return count;
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        return c.shape.accept(this);
    }
}
