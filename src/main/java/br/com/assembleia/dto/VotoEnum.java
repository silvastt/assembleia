package br.com.assembleia.dto;

public enum VotoEnum {

    SIM("Sim"),
    NAO("Não");

    private String descricao;

    VotoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
