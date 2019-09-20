package parcels;

import characters.Panda;
import engines.Logger;

public class ClassicParcel extends Parcel {

    private int heightBamboo;
    private ColorList colorList;
    private ImprovementList improvement;

    public ClassicParcel(ColorList colorList, ImprovementList improvement){
        this.heightBamboo = 0;
        this.colorList = colorList;
        setImprovement(improvement);
    }

    public ClassicParcel(ColorList colorList){
        this.heightBamboo = 0;
        this.colorList = colorList;
        this.improvement = null;
    }

    /**
     * @return le nombre de bambous sur la parcelle.
     */
    public int getHeightBamboo() { return this.heightBamboo; }

    /**
     * Fait pousser 0, 1 ou 2 bambous en fonction des caractéristiques de la parcelle.
     */
    public void growBamboo() {
        if(canBambooGrow()) {
            if (this.heightBamboo < 4) {
                Logger.printLine(growingSpeed + " bambou(s) ajouté sur la parcelle ["+(coordinates[0]-27)+", "+(coordinates[1]-27)+"]");
                this.heightBamboo += growingSpeed;
            }
            if (this.heightBamboo > 0 && this.improvement != ImprovementList.enclosure)
                canEat = true;
        }else
            Logger.printLine("Impossible de faire pousser un bambou en ["+(coordinates[0]-27)+", "+(coordinates[1]-27)+"] car la parcelle n'est pas irrigué ou il y a déjà 4 bambous.");
    }

    /**
     * Enlève 0 ou 1 bambou en fonction des caractéristiques de la parcelle.
     */
    public void eatBamboo() {
        if(this.heightBamboo > 0 && improvement != ImprovementList.enclosure) {
            this.heightBamboo--;
            Logger.printLine("Le panda a collecté un bambou " + Panda.getInstance().getPosition().getColor() + ".");
            if (this.heightBamboo == 0){
                canEat = false;
            }
        }
    }

    /**
     * @return la couleur de la parcelle.
     */
    public ColorList getColor(){
        return this.colorList;
    }

    /**
     * @return le type d'aménagement que possède la parcelle.
     */
    public ImprovementList getImprovement() { return this.improvement; }

    /**
     * Applique un aménagement sur la parcelle.
     * @param improvement aménagement que l'on souhaite mettre en place.
     */
    public void setImprovement(ImprovementList improvement) {
        this.improvement = improvement;
        switch(improvement) {
            case fertilizer:
                growingSpeed = 2;
                break;
            case enclosure:
                canEat = false;
                break;
            case pond:
                irrigated = true;
                break;
        }
    }
}
