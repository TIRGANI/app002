package beans;

public class Star {
    private static int comp;
    private int id;
    private String nom;
    private String prenom;
    private String ville;
    private String sexe;
    private String phone;

    public String getPhone() {
        return phone;
    }
    public Star()
    {

    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    private int img;
    private float star;

    public Star(String nom,String prenom,String ville,String sexe, int img, float star) {
        this.id = ++comp;
        this.nom = nom;
        this.img = img;
        this.star = 5;
        this.ville = ville;
        this.prenom= prenom;
        this.sexe = sexe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nom;
    }

    public void setName(String nom) {
        this.nom = nom;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = 5;
    }
}


