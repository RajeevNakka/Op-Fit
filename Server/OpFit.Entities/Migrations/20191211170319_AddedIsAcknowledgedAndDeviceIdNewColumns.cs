using Microsoft.EntityFrameworkCore.Migrations;

namespace OpFit.Entities.Migrations
{
    public partial class AddedIsAcknowledgedAndDeviceIdNewColumns : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "DeviceId",
                table: "Users",
                nullable: true);

            migrationBuilder.AddColumn<bool>(
                name: "IsAcknowledged",
                table: "HeartRateReadings",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "IsAcknowledged",
                table: "GpsReadings",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "IsAcknowledged",
                table: "AccelerationReadings",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "DeviceId",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "IsAcknowledged",
                table: "HeartRateReadings");

            migrationBuilder.DropColumn(
                name: "IsAcknowledged",
                table: "GpsReadings");

            migrationBuilder.DropColumn(
                name: "IsAcknowledged",
                table: "AccelerationReadings");
        }
    }
}
