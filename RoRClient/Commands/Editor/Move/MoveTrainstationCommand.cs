using RoRClient.Commands.Base;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Session;
using RoRClient.Models.Game;
using Newtonsoft.Json.Linq;

namespace RoRClient.Commands.Editor.Move
{
    class MoveTrainstationCommand : CommandBase
    {
        private int oldXPos;
        private int oldYPos;
        private int newXPos;
        private int newYPos;

        private int oldStockXPos;
        private int oldStockYPos;
        private int newStockXPos;
        private int newStockYPos;

        private List<Dictionary<string, object>> trainstationRailsCoordinates = new List<Dictionary<string, object>>();

		public MoveTrainstationCommand(RoRSession session, MessageInformation message) : base(session, message)
        {
            oldXPos = message.GetValueAsInt("oldXPos");
            oldYPos = message.GetValueAsInt("oldYPos");
            newXPos = message.GetValueAsInt("newXPos");
            newYPos = message.GetValueAsInt("newYPos");

            oldStockXPos = message.GetValueAsInt("oldStockXPos");
            oldStockYPos = message.GetValueAsInt("oldStockYPos");
            newStockXPos = message.GetValueAsInt("newStockXPos");
            newStockYPos = message.GetValueAsInt("newStockYPos");

            String jsonString = message.GetValueAsString("trainstationRailsCoordinates");
			JArray jsonArray = JArray.Parse(jsonString);
			foreach (JArray jsonList in jsonArray)
			{
				Dictionary<string, object> trainstationRailCoordinate = new Dictionary<string, object>();
				trainstationRailCoordinate.Add("railId", jsonList[0].ToString());
				trainstationRailCoordinate.Add("x", (int)jsonList[1]);
				trainstationRailCoordinate.Add("y", (int)jsonList[2]);
				trainstationRailsCoordinates.Add(trainstationRailCoordinate);
			}
		}

        public override void Execute()
        {
            EditorSession editorSession = (EditorSession)session;
			Square oldTrainstationSquare = editorSession.Map.GetSquare(oldXPos, oldYPos);
			Square newTrainstationSquare = editorSession.Map.GetSquare(newXPos, newYPos);
			Playertrainstation trainstation = (Playertrainstation)oldTrainstationSquare.PlaceableOnSquare;
			trainstation.Square = newTrainstationSquare;
            newTrainstationSquare.PlaceableOnSquare = trainstation;
            oldTrainstationSquare.PlaceableOnSquare = null;

            Square oldStockSquare = editorSession.Map.GetSquare(oldStockXPos, oldStockYPos);
            Square newStockSquare = editorSession.Map.GetSquare(newStockXPos, newStockYPos);
            Stock stock = (Stock)oldStockSquare.PlaceableOnSquare;
            stock.Square = newStockSquare;
            newStockSquare.PlaceableOnSquare = stock;
            oldStockSquare.PlaceableOnSquare = null;

            foreach (Dictionary<string, object> trainstationRailCoordinate in trainstationRailsCoordinates)
			{
				Rail rail = (Rail)editorSession.Map.GetPlaceableById(Guid.Parse((String)trainstationRailCoordinate["railId"]));
				int newXPos = (int)trainstationRailCoordinate["x"];
				int newYPos = (int)trainstationRailCoordinate["y"];
				Square oldRailSquare = editorSession.Map.GetSquare(rail.Square.PosX, rail.Square.PosY);
				Square newRailSquare = editorSession.Map.GetSquare(newXPos, newYPos);

				// lösche das PlaceableOnSquare vom oldSquare
				oldRailSquare.PlaceableOnSquare = null;
				// füge der Rail die neue Square hinzu
				rail.Square = newRailSquare;
				// füge dem neu zugehörigen Square die Rail als PlaceableOnSquare hinzu
				newRailSquare.PlaceableOnSquare = rail;
			}
		}
    }
}
