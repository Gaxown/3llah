package org.consultationsys.models.enums;

public enum ProcedureType {
    RADIOGRAPH("Radiography", 80.0),
    CHOREOGRAPH("Choreography", 120.0),
    IRM("MRI", 800.0),
    ELECTROCARDIOGRAM("Electrocardiogram", 100.0),
    LASER_DERMATOLOGY("Laser Dermatology", 300.0),
    FOND_OIL("Fundus", 150.0),
    BLOOD_TEST("Blood Test", 50.0),
    URINE_TEST("Urine Test", 40.0);

    public String label;
    public double standardPrice;

    ProcedureType(String label, double standardPrice) {
        this.label = label;
        this.standardPrice = standardPrice;
    }

    public String getLabel() {
        return label;
    }

    public double getStandardPrice() {
        return standardPrice;
    }
}
