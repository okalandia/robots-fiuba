package modelo;

public enum Ficha {
	CRUZ {
    public String toString() {
      return "x";
    }
	}, 
	CIRCULO {
    public String toString() {
      return "o";
    }
	},
	VACIO {
    public String toString() {
      return "-";
    }
	},
	INVALIDO {
    public String toString() {
      return "";
    }
	};
	
	public static Ficha getIndex(int i) {
		return values()[i];
	}
}
