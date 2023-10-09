package entity;


import java.util.Objects;

public class Formation {
    private int numFormation;
    private String nomFormation;
    private String promotion;

    public Formation(int numFormation, String nomFormation, String promotion) {
        super();
        this.numFormation = numFormation;
        this.nomFormation = nomFormation;
        this.promotion = promotion;
    }

    public int getNumFormation() {
        return this.numFormation;
    }

    public void setNumFormation(int numFormation) {
        this.numFormation = numFormation;
    }

    public String getNomFormation() {
        return this.nomFormation;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public String getPromotion() {
        return this.promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "Formation [numFormation=" + numFormation + ", nomFormation=" + nomFormation + ", promotion=" + promotion
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(numFormation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Formation other = (Formation) obj;
        return numFormation == other.numFormation;
    }
}
