CREATE TABLE IF NOT EXISTS Sujet (
  `id` INTEGER NOT NULL,
  `nom` TEXT NOT NULL,
  `score` INTEGER NULL,
  `taille` INTEGER NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS Question(
  `id` INTEGER NOT NULL,
  `question` TEXT NOT NULL,
  `bonneReponse` INTEGER NULL,
  `sujet` INTEGER NULL,
  `image` TEXT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS Reponse(
  `id` INTEGER NOT NULL,
  `reponse` TEXT NOT NULL,
  `question` INTEGER NULL,
  PRIMARY KEY (`id`));
