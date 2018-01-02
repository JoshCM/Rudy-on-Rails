using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RoRClient.Commands.Base;
using RoRClient.Models.Session;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using Newtonsoft.Json.Linq;

namespace RoRClient.Commands.Game.Other

{
    /// <summary>
    /// StartCommand für Game
    /// </summary>
    public class StartGameCommand : CommandBase
    {
        public StartGameCommand(RoRSession session, MessageInformation messageInformation) : base(session, messageInformation)
        {

        }
        public override void Execute()
        {
            GameSession.GetInstance().Started = true;
        }
    }
}
