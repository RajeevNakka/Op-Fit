using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace OpFit.Entities.Migrations
{
    public partial class InitialCreate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    TimeStamp = table.Column<DateTime>(nullable: false),
                    UserName = table.Column<string>(nullable: true),
                    Password = table.Column<string>(nullable: true),
                    FirstName = table.Column<string>(nullable: true),
                    LastName = table.Column<string>(nullable: true),
                    Age = table.Column<long>(nullable: false),
                    Gender = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "AccelerationReadings",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    TimeStamp = table.Column<DateTime>(nullable: false),
                    UserId = table.Column<int>(nullable: false),
                    X = table.Column<int>(nullable: false),
                    Y = table.Column<int>(nullable: false),
                    Z = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AccelerationReadings", x => x.Id);
                    table.ForeignKey(
                        name: "FK_AccelerationReadings_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "GpsReadings",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    TimeStamp = table.Column<DateTime>(nullable: false),
                    UserId = table.Column<int>(nullable: false),
                    Longitude = table.Column<float>(nullable: false),
                    Latitude = table.Column<float>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_GpsReadings", x => x.Id);
                    table.ForeignKey(
                        name: "FK_GpsReadings_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "HeartRateReadings",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    TimeStamp = table.Column<DateTime>(nullable: false),
                    UserId = table.Column<int>(nullable: false),
                    Rate = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_HeartRateReadings", x => x.Id);
                    table.ForeignKey(
                        name: "FK_HeartRateReadings_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_AccelerationReadings_UserId",
                table: "AccelerationReadings",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_GpsReadings_UserId",
                table: "GpsReadings",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_HeartRateReadings_UserId",
                table: "HeartRateReadings",
                column: "UserId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "AccelerationReadings");

            migrationBuilder.DropTable(
                name: "GpsReadings");

            migrationBuilder.DropTable(
                name: "HeartRateReadings");

            migrationBuilder.DropTable(
                name: "Users");
        }
    }
}
