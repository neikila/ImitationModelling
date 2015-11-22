package storageModel;

import resourse.XMLParser;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 19.11.15.
 */
public class Storage {
    private List<Point> boundPoints;
    private List<Barrier> barriers;
    private List<Section> sections;
    private List<Rack> racks;
    private Point box;
    private GraphOfWays graph;

    public Storage(XMLParser parser) {
        box = new Point(2000, 2000);
        this.boundPoints = parser.getWallPoints();
        this.barriers = parser.getBarriers();
        this.racks = parser.getRacks();
        this.sections = new ArrayList<>();
        setSections();

        graph = new GraphOfWays(this);
    }

    public void setSections() {
        for (Rack rack: racks) {
            Point coord = rack.getCoordinate();
            Point sectionSize = rack.getSectionSize();
            Point size = rack.getSize();
            int sectionInRow = size.x / sectionSize.x;
            int sectionInColumn = size.y / sectionSize.y;
            for (int j = 0; j < sectionInColumn; ++j) {
                for (int i = 0; i < sectionInRow; ++i) {
                    for (int k = 0; k < rack.getLevel(); ++k) {
                        Point temp = new Point(coord);
                        temp.translate(box.x * i, box.y * j);
                        int[] x = new int[4];
                        int[] y = new int[4];
                        x[0] = temp.x;
                        y[0] = temp.y;
                        x[1] = temp.x + box.x;
                        y[1] = temp.y;
                        x[2] = temp.x + box.x;
                        y[2] = temp.y + box.y;
                        x[3] = temp.x;
                        y[3] = temp.y + box.y;
                        sections.add(
                                new Section(
                                        new Point(temp.x / box.x, temp.y / box.y),
                                        sectionSize,
                                        k,
                                        rack.getDirection(),
                                        new Polygon(x, y, 4)
                                )
                        );
                    }
                }
            }
        }

        for (Section section: sections) {
            if (section.getPossibleDirection() != null)
                continue;
            Point current;
            current = new Point(section.getCenter());
            current.translate(0, box.y);
            if (isEmpty(current)) {
                section.setPossibleDirection(Direction.Up);
                continue;
            }
            current = new Point(section.getCenter());
            current.translate(0, -box.y);
            if (isEmpty(current)) {
                section.setPossibleDirection(Direction.Down);
                continue;
            }
            current = new Point(section.getCenter());
            current.translate(box.x, 0);
            if (isEmpty(current)) {
                section.setPossibleDirection(Direction.Right);
                continue;
            }
            current = new Point(section.getCenter());
            current.translate(-box.x, 0);
            if (isEmpty(current)) {
                section.setPossibleDirection(Direction.Left);
                continue;
            }
        }

//        for (Section el: sections) {
//            if (el.getLevel() == 1) {
//                System.out.println(el.toString() + " Direction: " + el.getPossibleDirection());
//            }
//        }
    }

    public boolean isEmpty(Point point) {
        for (Barrier el: barriers) {
            if (el.getPolygon().contains(point))
                return false;
        }
        for (Section el: sections) {
            if(el.getPolygon().contains(point))
                return false;
        }
        return true;
    }

    public List<Point> getBoundPoints() {
        return boundPoints;
    }

    public List<Barrier> getBarriers() {
        return barriers;
    }

    public List<Section> getSections() {
        return sections;
    }

    public List<Rack> getRacks() {
        return racks;
    }

    public Point getBox() {
        return box;
    }

    public void printAllSections() {
        for (Section el: sections) {
            if (el.getLevel() == 0) {
                System.out.println("id: " + el.getId() + "; index[" + el.getIndex().x + ";" + el.getIndex().y + ']');
            }
        }
    }

    public void printAllWalls() {
        int size = boundPoints.size();
        for (int i = 0; i < size; ++i) {
            System.out.format("a = [%d:%d]; b = [%d:%d]%n",
                    boundPoints.get(i).x, boundPoints.get(i).y,
                    boundPoints.get((i + 1) % size).x, boundPoints.get((i + 1) % size).y
            );
        }
    }

    public void printAllBarriers() {
        for (Barrier barrier : barriers) {
            System.out.println(barrier);
        }
    }
}
