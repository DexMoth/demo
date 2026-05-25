import os

output_Path = ""
template_model = "package com.example.demo.models;\n\nimport jakarta.persistence.*;\nimport jakarta.validation.constraints.NotBlank;\n\nimport java.util.Objects;\n\n@Entity\npublic class <EntityName> {\n    @Id\n    @GeneratedValue(strategy = GenerationType.AUTO)\n    private long id;\n<AddColumns>\n    //Constructor\n    public <EntityName>(){}\n<AddConstructor>\n\t//Override\n    @Override\n    public boolean equals(Object o) {\n        if (this == o) return true;\n        if (o == null || getClass() != o.getClass()) return false;\n        <EntityName> <entityName> = (<EntityName>) o;\n        return Objects.equals(id, <entityName>.id);\n    }\n    @Override\n    public int hashCode() {return Objects.hash(id);}\n    //Get\n    public long getId() {\n        return id;\n    }\n<AddGetters>\t//Set\n<AddSetters>\n}"
template_repository = "package com.example.demo.repositories;\n\nimport com.example.demo.models.<EntityName>;\nimport org.springframework.data.jpa.repository.JpaRepository;\n\nimport java.util.Optional;\n\npublic interface <EntityName>Repository extends JpaRepository<<EntityName>, Long> {\n}\n"
template_DTO = "package com.example.demo.DTOs;\n\nimport com.example.demo.models.<EntityName>;\nimport com.fasterxml.jackson.annotation.JsonProperty;\n\npublic class <EntityName>DTO {\n    private long id;\n<AddFields>\t//Constructor\n    public <EntityName>DTO(){}\n    public <EntityName>DTO(<EntityName> <entityName>){\n        this.id = <entityName>.getId();\n<AddFieldTransfer>\n    }\n    //Get\n    @JsonProperty(access = JsonProperty.Access.READ_ONLY)\n    public long getId() {return id;}\n<AddGetters>\t//Set\n<AddSetters>\n}"
template_service = "package com.example.demo.services;\n\nimport com.example.demo.models.<EntityName>;\nimport com.example.demo.repositories.<EntityName>Repository;\nimport com.example.demo.utils.validation.ValidationException;\nimport com.example.demo.utils.validation.ValidatorUtil;\nimport org.springframework.stereotype.Service;\nimport org.springframework.transaction.annotation.Transactional;\n\nimport java.lang.module.FindException;\nimport java.util.Collections;\nimport java.util.List;\n\n@Service\npublic class <EntityName>Services{\n    private final <EntityName>Repository <entityName>Repository;\n    private final ValidatorUtil validatorUtil;\n    //Constructor\n    public <EntityName>Services(<EntityName>Repository <entityName>Repository, ValidatorUtil validatorUtil){\n        this.<entityName>Repository = <entityName>Repository;\n        this.validatorUtil = validatorUtil;\n    }\n    //Methods\n    public <EntityName> add<EntityName>(<AddArgs>){\n\n        <EntityName> <entityName> = new <EntityName>(<CreatingArgs>);\n        validatorUtil.validate(<entityName>);\n        return <entityName>Repository.save(<entityName>);\n    }\n    @Transactional\n    public <EntityName> find<EntityName>(Long id){\n        return <entityName>Repository.findById(id).orElseThrow(()->new FindException(\"<EntityName> with id \"+id+\" do not find\"));\n    }\n    @Transactional\n    public List<<EntityName>> getAll<EntityName>s(){\n        return <entityName>Repository.findAll();\n    }\n    @Transactional\n    public <EntityName> update<EntityName>(Long id, <UpdateArgs>){\n        <EntityName> <entityName> = find<EntityName>(id);\n<ChangeFieldValue>\t\tvalidatorUtil.validate(<entityName>);\n        return <entityName>Repository.save(<entityName>);\n    }\n    @Transactional\n    public <EntityName> delete<EntityName>(Long id){\n        <EntityName> <entityName> = find<EntityName>(id);\n        <entityName>Repository.delete(<entityName>);\n        return <entityName>;\n    }\n    @Transactional\n    public void deleteAll(){\n        <entityName>Repository.deleteAll();\n    }\n}"
template_controller = "package com.example.demo.controllers;\n\nimport com.example.demo.DTOs.<EntityName>DTO;\nimport com.example.demo.configurations.WebConfiguration;\nimport com.example.demo.services.<EntityName>Services;\nimport io.swagger.v3.oas.annotations.Operation;\nimport io.swagger.v3.oas.annotations.tags.Tag;\nimport jakarta.servlet.http.HttpServletRequest;\nimport jakarta.validation.Valid;\nimport org.springframework.web.bind.annotation.*;\n\nimport java.util.List;\n\n@RestController\n@RequestMapping(WebConfiguration.REST_API +\"/<entityName>\")\npublic class <EntityName>Controller {\n    private final <EntityName>Services <entityName>Services;\n\n    public <EntityName>Controller(<EntityName>Services <entityName>Services){\n        this.<entityName>Services = <entityName>Services;\n    }\n    @GetMapping\n    public List<<EntityName>DTO> get<EntityName>s(){\n        return <entityName>Services.getAll<EntityName>s().stream().map(<EntityName>DTO::new).toList();\n    }\n    @PostMapping\n    public <EntityName>DTO create<EntityName>(@RequestBody @Valid <EntityName>DTO <entityName>DTO){\n        return new <EntityName>DTO(<entityName>Services.add<EntityName>(<GetFromDTOToCreate>));\n    }\n    @GetMapping(\"/{id}\")\n    public <EntityName>DTO get<EntityName>(@PathVariable Long id){\n        return new <EntityName>DTO(<entityName>Services.find<EntityName>(id));\n    }\n    @PutMapping(\"/{id}\")\n    public <EntityName>DTO update<EntityName>(@PathVariable Long id, @RequestBody @Valid <EntityName>DTO <entityName>DTO){\n        return new <EntityName>DTO(<entityName>Services.update<EntityName>(id, <GetFromDTOToUpdate>));\n    }\n    @DeleteMapping(\"/{id}\")\n    public <EntityName>DTO delete<EntityName>(@PathVariable Long id){\n        return new <EntityName>DTO(<entityName>Services.delete<EntityName>(id));\n    }\n}"


def firstLower(st):
    return st[0].lower() + st[1:]


def firstUpper(st):
    return st[0].upper() + st[1:]


def CreateFolders():
    if output_Path != "" and not os.path.isdir(output_Path):
        os.mkdir(output_Path)
    if not os.path.isdir(f'{output_Path}\\models'):
        os.mkdir(f'{output_Path}\\models')
    if not os.path.isdir(f'{output_Path}\\repositories'):
        os.mkdir(f'{output_Path}\\repositories')
    if not os.path.isdir(f'{output_Path}\\DTOs'):
        os.mkdir(f'{output_Path}\\DTOs')
    if not os.path.isdir(f'{output_Path}\\services'):
        os.mkdir(f'{output_Path}\\services')
    if not os.path.isdir(f'{output_Path}\\controllers'):
        os.mkdir(f'{output_Path}\\controllers')


CreateFolders()
ent_name = input("Введите название сущности: ")

model = template_model.replace("<EntityName>", ent_name)
model = model.replace("<entityName>", ent_name.lower())

print("Введите поля в формате 'тип_поля НазваниеПоля n=[f]/t u=[f]/t s=f/[t]'(n - nullable, u - unique, s-setter)"
      " Для завершения отправить пустую строчку")

count = 0
ent_fields = ""
ent_dto_fields = ""
ent_get = ""
ent_dto_get = ""
ent_set = ""
ent_constructor = ""
ent_constructor_args = ""
ent_constructor_body = ""
ent_dto_constructor_body = ""
ent_add_params = ""
ent_update_args = ""
ent_update_body = ""
ent_from_DTO_to_create = ""
ent_from_DTO_to_update = ""

while True:
    line = input(f'Поле {count}: ')
    count += 1

    if line == "":
        break
    nullable = False
    unique = False
    tmp = line.split(" ")
    tmp[1] = firstUpper(tmp[1])
    if line.find('n=t') != -1:
        nullable = True
    if line.find("u=t") != -1:
        unique = True
    if line.find("s=f") == -1:
        ent_set += f'\tpublic void set{tmp[1]}({tmp[0]} {firstLower(tmp[1])})' \
                   '{this.' + f'{firstLower(tmp[1])} = {firstLower(tmp[1])}' + ';}\n'
        ent_update_body += f'\t\t{firstLower(ent_name)}.set{tmp[1]}({firstLower(tmp[1])});\n'
        if ent_update_args != "":
            ent_update_args += ', '
            ent_from_DTO_to_update += ', '
        ent_update_args += f'{tmp[0]} {firstLower(tmp[1])}'
        ent_from_DTO_to_update += f'{firstLower(ent_name)}DTO.get{tmp[1]}()'
    else:
        ent_dto_get +='\t@JsonProperty(access = JsonProperty.Access.READ_ONLY)\n'

    if ent_constructor_args != "":
        ent_constructor_args += ', '
        ent_add_params += ', '
        ent_from_DTO_to_create += ', '
    ent_constructor_args += f'{tmp[0]} {firstLower(tmp[1])}'
    ent_add_params += firstLower(tmp[1])
    ent_from_DTO_to_create += f'{firstLower(ent_name)}DTO.get{tmp[1]}()'
    ent_constructor_body += f'\t\tthis.{firstLower(tmp[1])} = {firstLower(tmp[1])};\n'
    ent_dto_constructor_body += f'\t\tthis.{firstLower(tmp[1])} = {firstLower(ent_name)}.get{tmp[1]}();\n'

    ent_dto_fields += f'\tprivate {tmp[0]} {firstLower(tmp[1])};\n'
    ent_fields += f'\t@Column(nullable = {str(nullable).lower()},  unique = {str(unique).lower()})\n' \
                  f'\tprivate {tmp[0]} {firstLower(tmp[1])};\n'

    ent_get += f'\tpublic {tmp[0]} get{tmp[1]}()' + '{return ' + firstLower(tmp[1]) + ';}\n'
    ent_dto_get += f'\tpublic {tmp[0]} get{tmp[1]}()' + '{return ' + firstLower(tmp[1]) + ';}\n'

with open(f'{output_Path}\\models\\{ent_name}.java', 'w') as Model:
    model = model.replace("<AddGetters>", ent_get)
    model = model.replace("<AddColumns>", ent_fields)
    ent_constructor = f'\tpublic {ent_name}({ent_constructor_args})' + '{\n' + ent_constructor_body + "\t}"
    model = model.replace("<AddConstructor>", ent_constructor)
    model = model.replace("<AddSetters>", ent_set)
    Model.write(model)

with open(f'{output_Path}\\repositories\\{ent_name}Repository.java', 'w') as Repository:
    repository = template_repository.replace("<EntityName>", ent_name)
    Repository.write(repository)

with open(f'{output_Path}\\DTOs\\{ent_name}DTO.java', 'w') as DTO:
    dto = template_DTO.replace("<EntityName>", ent_name)
    dto = dto.replace("<entityName>", firstLower(ent_name))
    dto = dto.replace("<AddFields>", ent_dto_fields)
    dto = dto.replace("<AddGetters>", ent_dto_get)
    dto = dto.replace("<AddSetters>", ent_set)
    dto = dto.replace("<AddFieldTransfer>", ent_dto_constructor_body)
    DTO.write(dto)

with open(f'{output_Path}\\services\\{ent_name}Services.java', 'w') as Services:
    services = template_service.replace("<EntityName>", ent_name)
    services = services.replace("<entityName>", firstLower(ent_name))
    services = services.replace("<AddArgs>", ent_constructor_args)
    services = services.replace("<UpdateArgs>", ent_update_args)
    services = services.replace("<CreatingArgs>", ent_add_params)
    services = services.replace("<ChangeFieldValue>", ent_update_body)
    Services.write(services)

with open(f'{output_Path}\\controllers\\{ent_name}Controller.java', 'w') as Controller:
    controller = template_controller.replace("<EntityName>", ent_name)
    controller = controller.replace("<entityName>", firstLower(ent_name))
    controller = controller.replace("<GetFromDTOToCreate>", ent_from_DTO_to_create)
    controller = controller.replace("<GetFromDTOToUpdate>", ent_from_DTO_to_update)
    Controller.write(controller)
