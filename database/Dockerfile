# Use the official Microsoft SQL Server image
FROM mcr.microsoft.com/mssql/server:2019-latest

# Set environment variables
ENV SA_PASSWORD=YourStrong!Passw0rd
ENV ACCEPT_EULA=Y

# Copy the database files
COPY greenshop3.mdf /var/opt/mssql/data/
COPY greenshop3_log.ldf /var/opt/mssql/data/

# Run SQL Server and attach the database
CMD /opt/mssql/bin/sqlservr