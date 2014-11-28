-- -----------------------------------------------------------------------
-- mysql SQL script for schema mvcdb
-- -----------------------------------------------------------------------


drop table if exists candidato;
drop table if exists usuario;



# -----------------------------------------------------------------------
# candidato
# -----------------------------------------------------------------------
CREATE TABLE candidato
(
    candidato_id INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(128) NOT NULL,
    num_votos INTEGER NOT NULL,
    PRIMARY KEY(candidato_id)
);


# -----------------------------------------------------------------------
# usuario
# -----------------------------------------------------------------------
CREATE TABLE usuario
(
    usuario_id INTEGER NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(128) NOT NULL,
    password VARCHAR(128) NOT NULL,
    rol VARCHAR(128) NOT NULL,
    PRIMARY KEY(usuario_id)
);





