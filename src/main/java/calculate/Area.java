package calculate;

public class Area implements Material {
    private Integer length;
    private Integer width;
    private Integer SurfaceArea;

    public Area(Integer length, Integer wight) {
        this.length = length;
        this.width = wight;
    }

    public Integer getLength() {
        return this.length;
    }

    public Integer getWight() {
        return this.width;
    }

    public Integer getArea() {
        return this.width * this.length;
    }
}
