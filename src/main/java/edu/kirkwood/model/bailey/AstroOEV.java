package edu.kirkwood.model.bailey;

/** ***ChatGPT used for Javadocs***
 * Represents an astrophotography calculator used to calculate
 * Overall Exposure Value (OEV) and image quality.
 */
public class AstroOEV implements Comparable<AstroOEV> {

    private double subExposureTime;
    private int frameCount;
    private double fStop;
    private String sensorSize;
    private int bortle;

    /**
     * Creates an AstroOEV calculator with the specified astrophotography
     * settings.
     *
     * @param subExposureTime the exposure time for each light frame, in seconds
     * @param fStop the f-stop (aperture) used for the exposure
     * @param frameCount the number of light frames
     * @param sensorSize the size/type of the camera sensor
     * @param bortle the Bortle class of the observing location
     */
    public AstroOEV(double subExposureTime, double fStop, int frameCount,
                    String sensorSize, int bortle){
        setSubExposureTime(subExposureTime);
        setFStop(fStop);
        setFrameCount(frameCount);
        setSensorSize(sensorSize);
        setBortle(bortle);
    }

    /**
     * Calculates the Overall Exposure Value (OEV) using
     * exposure settings, frame count, and sensor size.
     *
     * @return the calculated Overall Exposure Value
     */
    public double calcOEV(){
        return Math.round(100 * (calcIntegrationTime() / (fStop*fStop)) * calcSensorMultiplier());
    }

    /**
     * Calculates the total integration time by multiplying the
     * sub-exposure time by the number of light frames.
     *
     * @return the total integration time in seconds
     */
    public double calcIntegrationTime(){  // **SECONDS**
        return subExposureTime * frameCount;
    }

    /**
     * Calculates the image quality rating based on the
     * astrophotography conditions.
     *
     * @return the image quality rating
     */
    public String calcImageQuality(){
        double bortleMultiplier = (9.5319 * (Math.pow(0.9884, (bortle-1)))) - 8.5319;  // 1 = no light pollution. More light pollution decreases the multiplier.
        double score = calcIntegrationTime() * bortleMultiplier;

        if(12000 <= score){
            return "Excellent";
        } else if (3000 <= score) {
            return "Good";
        } else if (900 <= score) {
            return "Fair";
        } else if (0 < score) {
            return "Poor";
        } else {
            throw new IllegalStateException("OEV calculation error. Please try again.");
        }
    }

    /**
     * Determines the multiplier associated with the selected
     * camera sensor size.
     *
     * @return the sensor size multiplier
     */
    public double calcSensorMultiplier(){
        double sensorMultiplier;
        if(sensorSize.equalsIgnoreCase("micro")){
            return 0.1549;
        } else if(sensorSize.equalsIgnoreCase("apsc") || sensorSize.equalsIgnoreCase("aps-c")){
            return 0.2525;
        } else if(sensorSize.equalsIgnoreCase("full")){
            return 0.595;
        } else if(sensorSize.equalsIgnoreCase("medium")){
            return 1;
        }
        // FailSafe
        throw new IllegalArgumentException("Unsupported sensor size. Valid sensors include: 'micro', 'apsc', 'full', and 'medium'.");
    }

    public static String sensorDisplayName(String sensorSize){
        if(sensorSize.equals("micro")){
            return "Micro Four-thirds";
        } else if(sensorSize.equalsIgnoreCase("apsc") || sensorSize.equalsIgnoreCase("aps-c")){
            return "APS-C";
        } else if(sensorSize.equals("full")){
            return "Full-Frame";
        } else{
            return "Medium Format";
        }
    }
    // ______________________________________________________
    // ------------------ GETTERS & SETTERS ------------------

    public double getSubExposureTime() {return subExposureTime;}
    public void setSubExposureTime(double subExposureTime) {
        if(subExposureTime <= 0){
            throw new IllegalArgumentException("Sub Exposure Time must be greater than Zero.");
        }
        this.subExposureTime = subExposureTime;
    }

    public double getFStop() {return fStop;}
    public void setFStop(double fStop) {
        if(fStop <= 0){
            throw new IllegalArgumentException("F-stop must be greater than zero.");
        }
        this.fStop = fStop;
    }

    public int getFrameCount() {return frameCount;}
    public void setFrameCount(int frameCount) {
        if(frameCount < 1){
            throw new IllegalArgumentException("Frame count must be at least 1.");
        }
        this.frameCount = frameCount;
    }

    public String getSensorSize() {return sensorSize;}
    public void setSensorSize(String sensorSize) {
        if(sensorSize == null || sensorSize.isBlank()){
            throw new IllegalArgumentException("Sensor Size cannot be left blank.");
        }else if(!sensorSize.equalsIgnoreCase("micro") &&
                !sensorSize.equalsIgnoreCase("apsc") &&
                !sensorSize.equalsIgnoreCase("aps-c") &&
                !sensorSize.equalsIgnoreCase("full") &&
                !sensorSize.equalsIgnoreCase("medium")){
            throw new IllegalArgumentException("Unsupported sensor size. Valid sensors include: 'micro', 'apsc', 'full', and 'medium'.");
        }
        this.sensorSize = sensorSize;
    }

    public int getBortle() {return bortle;}
    public void setBortle(int bortle) {
        if(bortle < 1 || 9 < bortle){
            throw new IllegalArgumentException("Bortle value must be between 1 and 9.");
        }
        this.bortle = bortle;
    }

    /**
     * Compares this AstroOEV to another AstroOEV based on calculated OEV.
     *
     * @param other the AstroOEV to compare with this object
     * @return a negative value if this OEV is lower, zero if equal, or a positive value if higher
     */
    @Override
    public int compareTo(AstroOEV other) {
        return Double.compare(calcOEV(),
                other.calcOEV()
        );
    }

    /**
     * Compares this AstroOEV to another object for equality.
     *
     * @param other the object to compare with this AstroOEV
     * @return true if the objects have the same values for all attributes; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        AstroOEV astroOEV = (AstroOEV) other;
        return Double.compare(subExposureTime, astroOEV.subExposureTime) == 0
                && Double.compare(fStop, astroOEV.fStop) == 0
                && frameCount == astroOEV.frameCount
                && sensorSize.equalsIgnoreCase(astroOEV.sensorSize)
                && bortle == astroOEV.bortle;
    }
}