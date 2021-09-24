package free.imei.check.and.network.unlocker.Models;

public class ModelNames {

    private String modelName;
    private int modelImage;

    public ModelNames() {
    }

    public ModelNames(String modelName, int modelImage) {
        this.modelName = modelName;
        this.modelImage = modelImage;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getModelImage() {
        return modelImage;
    }

    public void setModelImage(int modelImage) {
        this.modelImage = modelImage;
    }
}
