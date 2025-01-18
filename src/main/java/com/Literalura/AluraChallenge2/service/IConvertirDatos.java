package com.Literalura.AluraChallenge2.service;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
