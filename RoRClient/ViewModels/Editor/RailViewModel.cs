﻿using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using RoRClient.ViewModels.Commands;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModels.Editor
{
    /// <summary>
    /// Hält die zugehörige Rail und die Position (SquarePosX, SquarePosY) des Rails
    /// </summary>
    public class RailViewModel : CanvasViewModel
    {
        private Rail rail;
        public RailViewModel(Rail rail) : base(rail.Id)
        {
            this.rail = rail;
            this.SquarePosX = rail.Square.PosX;
            this.SquarePosY = rail.Square.PosY;
        }

        public Rail Rail
        {
            get
            {
                return rail;
            }
        }

        public void SendRotateRightRailCommand()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", rail.Square.PosX);
            messageInformation.PutValue("yPos", rail.Square.PosY);
            messageInformation.PutValue("right", true);
            EditorSession.GetInstance().QueueSender.SendMessage("RotateRail", messageInformation);
        }

        public void SendRotateLeftRailCommand()
        {
            MessageInformation messageInformation = new MessageInformation();
            messageInformation.PutValue("xPos", rail.Square.PosX);
            messageInformation.PutValue("yPos", rail.Square.PosY);
            messageInformation.PutValue("right", false);
            EditorSession.GetInstance().QueueSender.SendMessage("RotateRail", messageInformation);
        }
    }
}
