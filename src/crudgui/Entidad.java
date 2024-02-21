package crudgui;

/**
 *
 * @author Miguel Antonio
 */
public class Entidad {
     private String id;
    private String nombre;
    private String tipo;
    private boolean marcado;

    public Entidad(String id, String nombre, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marcado = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void toggleMarcado() {
        marcado = !marcado;
    }
}
