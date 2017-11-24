namespace RoRClient.ViewModel
{
    class DummyMap
    {
        private DummySquare[,] squares;

        public DummySquare[,] Squares
        {
            get { return squares; }
        }

        public DummyMap(int dim)
        {
            squares = new DummySquare[dim, dim];

            for(int i = 0; i < dim; i++)
            {
                for(int j = 0; j < dim; j++)
                {
                    squares[i, j] = new DummySquare(i, j);

                    // Zum Test hier einfach mal ein DummyRail erzeugen, um es testweise anzuzeigen
                    squares[i, j].Rail = new DummyRail();
                }             
            }
        }
    }
}
