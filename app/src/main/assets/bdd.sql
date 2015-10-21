CREATE TABLE IF NOT EXISTS Sujet (
  `id` INTEGER NOT NULL,
  `nom` TEXT NOT NULL,
  `questionnaire` INTEGER NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS Question(
  `id` INTEGER NOT NULL,
  `question` TEXT NOT NULL,
  `BonneReponse` INTEGER NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS Reponse(
  `id` INTEGER NOT NULL,
  `reponse` TEXT NOT NULL,
  `question` INTEGER NULL,
  PRIMARY KEY (`id`));


  INSERT INTO Sujet (nom) VALUES ("Animaux");
  INSERT INTO Sujet (nom) VALUES ("Pays");