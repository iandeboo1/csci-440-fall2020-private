
SELECT * FROM artists
WHERE ArtistId=10;

SELECT * FROM tracks JOIN albums ON tracks.AlbumId = albums.AlbumId JOIN artists ON albums.ArtistId = artists.ArtistId WHERE (tracks.Name LIKE '%green%' OR albums.Title LIKE '%green%' OR artists.Name LIKE '%green%');

SELECT * FROM albums WHERE Title LIKE '%green%';

SELECT *
FROM tracks
    JOIN playlist_track ON tracks.TrackId = playlist_track.TrackID
    JOIN playlists ON playlist_track.PlaylistId = playlists.PlaylistId
WHERE playlists.PlaylistId = 3;

SELECT TrackId
FROM playlists
         JOIN playlist_track ON playlists.PlaylistId = playlist_track.PlaylistId
WHERE playlists.PlaylistId=3;

SELECT bosses.FirstName AS BossName, employees.FirstName AS Name
FROM employees
         JOIN employees AS bosses ON employees.ReportsTo = bosses.EmployeeId;

SELECT bosses.*
FROM employees
         JOIN employees AS bosses ON employees.ReportsTo = bosses.EmployeeId
WHERE employees.EmployeeId = 2;

SELECT FirstName
FROM employees
WHERE employees.ReportsTo = 2;

SELECT bosses.*
FROM employees
         JOIN employees AS bosses ON employees.ReportsTo = bosses.ReportsTo
WHERE employees.EmployeeId = 2;

SELECT *
FROM tracks;

SELECT *
FROM invoice_items
JOIN tracks ON invoice_items.TrackId = tracks.TrackId
JOIN albums ON tracks.AlbumId = albums.AlbumId
JOIN artists ON albums.ArtistId = artists.ArtistId
WHERE InvoiceId=15;

SELECT customers.FirstName AS customerName, employees.FirstName AS employeeName, bosses.FirstName AS bossName
FROM employees
JOIN employees AS bosses on employees.ReportsTo = bosses.EmployeeId
JOIN customers ON customers.SupportRepId = employees.EmployeeId
JOIN invoices ON invoices.CustomerId = customers.CustomerId
GROUP BY invoices.CustomerId
HAVING COUNT(InvoiceId) > 1;

CREATE TABLE "playlist_track"
(
    [PlaylistId] INTEGER  NOT NULL,
    [TrackId] INTEGER  NOT NULL,
    CONSTRAINT [PK_PlaylistTrack] PRIMARY KEY  ([PlaylistId], [TrackId]),
    FOREIGN KEY ([PlaylistId]) REFERENCES "playlists" ([PlaylistId])
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY ([TrackId]) REFERENCES "tracks" ([TrackId])
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

SELECT tracks.Name
FROM tracks
JOIN playlist_track ON tracks.TrackId = playlist_track.TrackId
JOIN playlists ON playlist_track.PlaylistId = playlists.PlaylistId
WHERE playlists.PlaylistId = 3;

SELECT COUNT(*) as ItemCount, InvoiceId
FROM invoice_items
GROUP BY InvoiceId
HAVING ItemCount > 10;

SELECT invoice_items.InvoiceLineId AS InvoiceLineId, invoice_items.InvoiceId AS InvoiceId, invoice_items.TrackId AS trackID,
       invoice_items.UnitPrice AS UnitPrice, invoice_items.Quantity AS Quantity, tracks.Name AS Name
FROM invoice_items
JOIN tracks ON invoice_items.TrackId = tracks.TrackId JOIN albums ON tracks.AlbumId = albums.AlbumId
JOIN artists ON albums.ArtistId = artists.ArtistId WHERE InvoiceId = 5;

SELECT COUNT(*) as SalesCount, employees.FirstName AS FirstName, employees.LastName AS LastName, employees.Email as Email, SUM(Total) AS SalesTotal
FROM invoices
JOIN customers ON invoices.CustomerId = customers.CustomerId
JOIN employees ON customers.SupportRepId = employees.EmployeeId
GROUP BY employees.EmployeeId
ORDER BY SalesCount DESC;

SELECT COUNT(*) as InvoiceCount
FROM invoices
JOIN customers ON invoices.CustomerId = customers.CustomerId
GROUP BY invoices.CustomerId
HAVING InvoiceCount > 3;

SELECT COUNT(*)
FROM invoices
JOIN customers ON invoices.CustomerId = customers.CustomerId
GROUP BY invoices.CustomerId;

/*Use the insert 4 times and then check with the select statement to test paging on customer detailed view*/

INSERT INTO invoices
(CustomerId, InvoiceDate, Total)
VALUES (2, 3, 4);

SELECT *
FROM invoices
WHERE CustomerId = 2;

/*Use the insert 4 times and then check with the select statement to test paging on customer detailed view*/

SELECT customers.Email
FROM customers
JOIN employees ON customers.SupportRepId = employees.EmployeeId
WHERE employees.FirstName = 'Jane' AND customers.CustomerId IN (SELECT customers.CustomerId
                                                                FROM customers
                                                                JOIN invoices ON customers.CustomerId = invoices.CustomerId
                                                                JOIN invoice_items ON invoices.InvoiceId = invoice_items.InvoiceId
                                                                JOIN tracks ON invoice_items.TrackId = tracks.TrackId
                                                                JOIN genres ON tracks.GenreId = genres.GenreId
                                                                WHERE genres.Name = 'Rock');

SELECT customers.CustomerId
FROM customers
JOIN invoices ON customers.CustomerId = invoices.CustomerId
JOIN invoice_items ON invoices.InvoiceId = invoice_items.InvoiceId
JOIN tracks ON invoice_items.TrackId = tracks.TrackId
JOIN genres ON tracks.GenreId = genres.GenreId
WHERE genres.Name = 'Rock';

SELECT *
FROM employees;

