using Microsoft.EntityFrameworkCore.Migrations;

namespace OpFit.Entities.Migrations
{
    public partial class IntToFloatChageForAcceleration : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<float>(
                name: "Z",
                table: "AccelerationReadings",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "Y",
                table: "AccelerationReadings",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "X",
                table: "AccelerationReadings",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "Z",
                table: "AccelerationReadings",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "Y",
                table: "AccelerationReadings",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "X",
                table: "AccelerationReadings",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));
        }
    }
}
