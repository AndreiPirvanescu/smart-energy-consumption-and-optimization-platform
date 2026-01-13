INSERT INTO `smartenergy`.`users` (`id`, `email`, `name`) VALUES ('1', 'andrei@yahoo.com', 'Andrei');
INSERT INTO `smartenergy`.`users` (`id`, `email`, `name`) VALUES ('2', 'marin@yahoo.com', 'Marin');
INSERT INTO `smartenergy`.`tariffs` (`id`, `name`, `rate_perkwh`) VALUES ('1', 'Commercial Standard', '0.82');
INSERT INTO `smartenergy`.`tariffs` (`id`, `name`, `rate_perkwh`) VALUES ('2', 'Commercial Peak Hours', '1.05');
INSERT INTO `smartenergy`.`buildings` (`id`, `address`, `name`, `user_id`, `tariff_id`) VALUES ('1', 'Strada Mieilor', 'Casa nr 13', 1, 1);
INSERT INTO `smartenergy`.`buildings` (`id`, `address`, `name`, `user_id`, `tariff_id`) VALUES ('2', 'Strada Palanca', 'Casa nr 5', 2, 1);
INSERT INTO `smartenergy`.`meters` (`id`, `installation_date`, `serial_number`, `building_id`) VALUES ('1', '2026-01-13', '123', 1);
INSERT INTO `smartenergy`.`meters` (`id`, `installation_date`, `serial_number`, `building_id`) VALUES ('2', '2026-01-14', '124', 2);
INSERT INTO `smartenergy`.`energy_readings` (`id`, `meter_id`, `consumption`, `timestamp`) VALUES ('1', '1', '40.5', '2026-01-13T10:30:00');
INSERT INTO `smartenergy`.`energy_readings` (`id`, `meter_id`, `consumption`, `timestamp`) VALUES ('2', '1', '50.5', '2026-01-14T10:30:00');
INSERT INTO `smartenergy`.`energy_readings` (`id`, `meter_id`, `consumption`, `timestamp`) VALUES ('3', '2', '30', '2026-01-13T10:30:00');
INSERT INTO `smartenergy`.`energy_readings` (`id`, `meter_id`, `consumption`, `timestamp`) VALUES ('4', '2', '35', '2026-01-14T10:30:00');
INSERT INTO `smartenergy`.`alerts` (`id`, `building_id`, `timestamp`, `message`, `severity`) VALUES ('1', '1', '2026-01-10 18:45:00', 'High energy consumption detected in the last hour.', 'High');
INSERT INTO `smartenergy`.`alerts` (`id`, `building_id`, `timestamp`, `message`, `severity`) VALUES ('2', '1', '2026-02-11 18:45:00', 'Energy usage exceeded daily threshold.', 'High');
INSERT INTO `smartenergy`.`alerts` (`id`, `building_id`, `timestamp`, `message`, `severity`) VALUES ('3', '1', '2026-01-10 18:45:00', 'Energy meter not reporting data consumption for more than 30 minutes.', 'Low');
INSERT INTO `smartenergy`.`alerts` (`id`, `building_id`, `timestamp`, `message`, `severity`) VALUES ('4', '2', '2026-02-11 18:45:00', 'Sudden drop in energy consumption detected.', 'High');




