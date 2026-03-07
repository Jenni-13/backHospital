package com.hospital.hospital.model.converter;

import com.hospital.hospital.model.entity.Paciente.TipoSangre;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)

public class TipoSangreConverter implements AttributeConverter<TipoSangre, String> {

    @Override
    public String convertToDatabaseColumn(TipoSangre tipoSangre) {

        if (tipoSangre == null)
            return null;

        return tipoSangre.getValor(); // guarda "O+", "AB-", etc.
    }

    @Override
    public TipoSangre convertToEntityAttribute(String valor) {

        if (valor == null)
            return null;
        return TipoSangre.fromValor(valor); // lee "O+" y devuelve O_POS

    }
}