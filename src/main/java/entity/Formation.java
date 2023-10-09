package entity;


import java.util.Objects;

public class Formation {
    private int idFormation;
    private String nomFormation;
    private String promotion;

    public Formation(int idFormation, String nomFormation, String promotion) {
        super();
        this.idFormation = idFormation;
        this.nomFormation = nomFormation;
        this.promotion = promotion;
    }

    public int getidFormation() {
        return this.idFormation;
    }

    public void setidFormation(int idFormation) {
        this.idFormation = idFormation;
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
        return "Formation [idFormation=" + idFormation + ", nomFormation=" + nomFormation + ", promotion=" + promotion
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFormation);
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
        return idFormation == other.idFormation;
    }
}
