DELETE FROM words
WHERE id IN (
    SELECT id 
    FROM words 
    ORDER BY id DESC 
    LIMIT 40
);