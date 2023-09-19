# SDAFinalProject
Final project repository for JavaRemoteEE26 group 3 for a Stock Tracker Application.

SQL triggers

CREATE DEFINER=`root`@`localhost` TRIGGER `after_insert_total_market_value_in_eur` 
AFTER INSERT ON `currency_rate` 
FOR EACH ROW BEGIN
    UPDATE Stock s
    JOIN Account a ON s.account_id = a.id
    SET s.total_market_value_in_eur = s.total_market_value / NEW.rate
    WHERE a.currency = NEW.currency;
END

CREATE DEFINER=`root`@`localhost` TRIGGER `update_balance_in_eur` 
AFTER INSERT ON `currency_rate` 
FOR EACH ROW BEGIN
    UPDATE account
    SET balance_in_eur = balance / NEW.rate
    WHERE currency = NEW.currency;
END

CREATE DEFINER=`root`@`localhost` TRIGGER `update_balance_in_eur_on_update` 
AFTER UPDATE ON `currency_rate` 
FOR EACH ROW BEGIN
    UPDATE account
    SET balance_in_eur = balance / NEW.rate
    WHERE currency = NEW.currency;
END

CREATE DEFINER=`root`@`localhost` TRIGGER `after_update_total_market_value_in_eur` 
AFTER UPDATE ON `currency_rate` 
FOR EACH ROW BEGIN
    UPDATE Stock s
    JOIN Account a ON s.account_id = a.id
    SET s.total_market_value_in_eur = s.total_market_value / NEW.rate
    WHERE a.currency = NEW.currency;
END
