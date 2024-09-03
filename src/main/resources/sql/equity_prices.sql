CREATE TABLE equity_prices (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255),
    ticker VARCHAR(255) NOT NULL,
    market VARCHAR(255),
    datetime DATETIME NOT NULL,
    open DOUBLE,
    high DOUBLE,
    low DOUBLE,
    close DOUBLE,
    volume DOUBLE,
    trading_currency VARCHAR(255),
    `interval` VARCHAR(255),
    source VARCHAR(255) NOT NULL,
    update_time DATE,
    CONSTRAINT unique_key_datetime_ticker_source_interval
        UNIQUE (datetime, ticker, source, `interval`)
)