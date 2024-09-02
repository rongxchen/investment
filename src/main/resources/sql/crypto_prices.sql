CREATE TABLE crypto_prices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ticker VARCHAR(255),
    market VARCHAR(255),
    datetime DATETIME,
    open DOUBLE,
    high DOUBLE,
    low DOUBLE,
    close DOUBLE,
    volume DOUBLE,
    trading_currency VARCHAR(255),
    `interval` VARCHAR(255),
    source VARCHAR(255),
    update_time DATE
)

ALTER TABLE crypto_prices
ADD CONSTRAINT unique_ticker_datetime_interval_source UNIQUE (ticker, datetime, `interval`, source)
