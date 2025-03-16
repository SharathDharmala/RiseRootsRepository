CREATE TABLE IF NOT EXISTS public.subjects (
    subject VARCHAR(255) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS public.schemas (
    id SERIAL PRIMARY KEY,
    schema VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.schema_version (
    id SERIAL PRIMARY KEY,
    subject VARCHAR(255) NOT NULL,
    version INT NOT NULL,
    schema_id INT NOT NULL REFERENCES public.schemas(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

select * from schemas;

SELECT * FROM schema_version;

SELECT * FROM subjects;

SELECT table_name FROM information_schema.tables WHERE table_name IN ('subjects', 'schemas', 'schema_version');