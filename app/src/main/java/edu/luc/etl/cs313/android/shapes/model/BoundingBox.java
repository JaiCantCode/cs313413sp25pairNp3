package edu.luc.etl.cs313.android.shapes.model;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // TODO entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.shape.accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        int x, y, width, height;
        x = y = Integer.MAX_VALUE;
        width = height = 0;

        for(Shape s : g.shapes) {
            Location l = s.accept(this);
            var r = (Rectangle) l.shape;
            if(l.x < x) x = l.x;
            if(l.y < y) y = l.y;
            if(x + width < l.x + r.width) width = (l.x + r.width) - x;
            if(y + height < l.y + r.height) height = (l.y + r.height) - y;
        }
        return new Location(x,y,new Rectangle(width, height));
    }

    @Override
    public Location onLocation(final Location l) {
        Location temp = l.shape.accept(this);
        return new Location(l.x + temp.x, l.y+ temp.y, temp.shape);
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0,0,new Rectangle(r.width, r.height));
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.shape.accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.shape.accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        int x, y, width, height;
        x = y = Integer.MAX_VALUE;
        width = height = 0;
        for(Point p : s.getPoints()) {
            if(p.x < x) x = p.x;
            if(p.y < y) y = p.y;
            if(p.x > width) width = p.x;
            if(p.y > height) height = p.y;
        }
        return new Location(x,y,new Rectangle(width-x, height-y));
    }
}
