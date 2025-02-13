// subklass av den generella verkstaden
// tar emot all typer av bilar
public class AllCarsWorkShop extends CarWorkshop<Car> {
    public AllCarsWorkShop(int maxCapacity) {
        super(maxCapacity);
    }
}
