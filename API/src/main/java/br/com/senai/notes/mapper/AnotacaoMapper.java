package br.com.senai.notes.mapper;

//package br.com.senai.notes.mapper;
//
//@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, TagMapper.class})
//public interface AnotacaoMapper {
//
//
//    @Mapping(source = "imagemAnotacao", target = "imagemAnotacao") // Exemplo de mapeamento explícito
//    @Mapping(source = "tagAnotacoes", target = "tags") // Mapeia o Set<TagAnotacao> para a List<ListarTagDTO>
//    ListarAnotacaoDTO toListarDto(Anotacao anotacao);
//
//    // Método auxiliar para o mapeamento complexo de TagAnotacao -> ListarTagDTO
//    default List<ListarTagDTO> mapTagAnotacoesToDto(Set<TagAnotacao> tagAnotacoes) {
//        if (tagAnotacoes == null) {
//            return List.of();
//        }
//        TagMapper tagMapper = new TagMapperImpl(); // Podemos instanciar ou injetar
//        return tagAnotacoes.stream()
//                .map(TagAnotacao::getIdTag) // Extrai o objeto Tag
//                .map(tagMapper::toDto)      // Converte Tag para ListarTagDTO
//                .collect(Collectors.toList());
//    }
//
//
//
//    // Ignoramos campos que serão definidos manualmente no service ou que não existem no DTO
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "usuario", ignore = true)
//    @Mapping(target = "dataCriacao", ignore = true)
//    @Mapping(target = "dataEdicao", ignore = true)
//    @Mapping(target = "anotacaoArquivada", ignore = true)
//    @Mapping(target = "tagAnotacoes", ignore = true)
//    @Mapping(source = "imagemUrl", target = "imagemAnotacao") // Mapeia campos com nomes diferentes
//    Anotacao toEntity(CadastroAnotacaoDTO dto);
//}
