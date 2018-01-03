
START TRANSACTION;

CREATE DATABASE quiz;

CREATE TABLE `answers` (
  `question_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `text` varchar(512) NOT NULL
);

CREATE TABLE `correct_answers` (
  `question_id` int(11) NOT NULL,
  `answer_id` int(11) NOT NULL
) ;


CREATE TABLE `question` (
  `quiz_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `text` varchar(512) NOT NULL,
  `value` int(11) NOT NULL DEFAULT '1'
) ;

CREATE TABLE `quiz` (
  `id` int(11) NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1'
) ;

CREATE TABLE `results` (
  `id` int(11) NOT NULL,
  `quiz_id` int(11) NOT NULL,
  `NIU` varchar(16) NOT NULL,
  `score` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `answers`
  ADD PRIMARY KEY (`question_id`,`id`);

ALTER TABLE `correct_answers`
  ADD UNIQUE KEY `question_id_2` (`question_id`),
  ADD KEY `question_id` (`question_id`,`answer_id`);

ALTER TABLE `question`
  ADD PRIMARY KEY (`quiz_id`,`id`);

ALTER TABLE `quiz`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `results`
  ADD KEY `quiz_id` (`quiz_id`);

ALTER TABLE `answers`
  ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`quiz_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `correct_answers`
  ADD CONSTRAINT `correct_answers_ibfk_1` FOREIGN KEY (`question_id`,`answer_id`) REFERENCES `answers` (`question_id`, `id`);

ALTER TABLE `question`
  ADD CONSTRAINT `question_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `results`
  ADD CONSTRAINT `results_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;
