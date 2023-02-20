package com.rader.salesmanager.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        /*modelMapper.createTypeMap(PedidoInput.ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));
*/
        return modelMapper;
    }
}
