package beans;

public class Contact {
    private int id;
    private String nom;
    private String prenom;
    private String ville;
    private String sexe;
    private String phone ;
    private int cpt =0;
    public Contact() {
    }
    public Contact(String nom, String prenom, String ville, String sexe, String phone) {
        this.id = ++cpt;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.sexe = sexe;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getSexe() {
        return sexe;
    }
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", ville='" + ville + '\'' +
                ", sexe='" + sexe + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
